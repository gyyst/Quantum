param (
    [string]$mode = "all",  # 模式: all - 构建所有项目, single - 构建单个项目
    [string]$module = "",   # 模块路径，例如: quantum-gateway 或 quantum-web/quantum-web-user
    [string]$tag = "latest" # 镜像标签
)

$ErrorActionPreference = "Stop"
$projectRoot = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
Set-Location $projectRoot

function Build-SingleProject {
    param (
        [string]$modulePath,
        [string]$tag
    )
    
    if (-not $modulePath) {
        Write-Error "请指定要构建的模块路径"
        exit 1
    }
    
    $moduleName = $modulePath -replace "/", "-"
    if ($modulePath -match "/") {
        $moduleName = ($modulePath -split "/")[-1]
    }
    
    Write-Host "开始构建模块: $modulePath, 镜像名称: quantum-$moduleName" -ForegroundColor Green
    
    # 创建临时Dockerfile
    $dockerfilePath = ".\deploey\docker\Dockerfile.$moduleName"
    $templateContent = Get-Content ".\deploey\docker\Dockerfile.template" -Raw
    $dockerfileContent = $templateContent -replace "MODULE_PATH", $modulePath
    Set-Content -Path $dockerfilePath -Value $dockerfileContent
    
    # 构建Docker镜像
    docker build -t "quantum-$moduleName`:$tag" -f $dockerfilePath .
    
    # 删除临时Dockerfile
    Remove-Item $dockerfilePath
    
    Write-Host "模块 $modulePath 构建完成" -ForegroundColor Green
}

function Build-AllProjects {
    param (
        [string]$tag
    )
    
    Write-Host "开始构建所有项目" -ForegroundColor Green
    
    # 查找所有需要构建的模块
    $gatewayModule = "quantum-gateway"
    $webModules = Get-ChildItem -Path ".\quantum-web" -Directory | ForEach-Object { "quantum-web/$($_.Name)" }
    
    # 构建gateway模块
    Write-Host "构建Gateway模块" -ForegroundColor Cyan
    docker build --target gateway -t "quantum-gateway:$tag" -f ".\deploey\docker\Dockerfile.all" .
    
    # 构建web模块
    foreach ($module in $webModules) {
        $moduleName = ($module -split "/")[-1]
        Write-Host "构建Web模块: $moduleName" -ForegroundColor Cyan
        docker build --target $moduleName -t "quantum-$moduleName`:$tag" -f ".\deploey\docker\Dockerfile.all" .
    }
    
    Write-Host "所有项目构建完成" -ForegroundColor Green
}

# 主逻辑
if ($mode -eq "all") {
    Build-AllProjects -tag $tag
} elseif ($mode -eq "single") {
    Build-SingleProject -modulePath $module -tag $tag
} else {
    Write-Error "无效的模式: $mode, 请使用 'all' 或 'single'"
    exit 1
}