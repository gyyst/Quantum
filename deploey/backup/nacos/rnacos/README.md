# rNacos 自动备份

这个 GitHub Action 工作流用于自动备份 rNacos 数据，包括数据下载、加密和上传到仓库的功能。

## 功能特点

- 🕐 **定时备份**：每天凌晨2点自动执行备份（UTC时间）
- 🔐 **数据加密**：使用 AES-256-CBC 加密算法保护备份数据
- 📁 **自动上传**：备份文件自动上传到 `quantum/deploey/backup/nacos/rnacos/backup` 目录
- 🗑️ **自动清理**：自动删除超过7天的旧备份文件
- 🚀 **手动触发**：支持手动触发备份操作

## 使用方法

### 1. 设置必要的Secrets

为了使备份工作流正常运行，需要在GitHub仓库中设置以下Secrets：

1. 进入 GitHub 仓库的 Settings 页面
2. 选择 Secrets and variables > Actions
3. 点击 New repository secret
4. 创建以下两个Secrets：

   - `RNACOS_BACKUP_URL`：rNacos备份API的完整URL
     ```
     http://127.0.0.1:8848/rnacos/backup?token=xxxxx
     ```

   - `AES_ENCRYPTION_KEY`（可选）：用于加密备份文件的密钥
     ```
     Quantum_rNacos_Backup_2024
     ```

> **注意**：如果不设置 `AES_ENCRYPTION_KEY`，系统将使用默认密钥，安全性较低。

### 2. 创建backup分支

在首次使用备份功能前，需要创建一个名为`backup`的分支：

1. 在本地仓库中执行：
   ```bash
   git checkout -b backup
   git push -u origin backup
   ```

2. 或者在GitHub网页上创建新分支：
   - 进入仓库页面
   - 点击分支下拉菜单
   - 输入"backup"并创建新分支

### 3. 启用工作流

工作流文件已创建在 `.github/workflows/rnacos-backup.yml`，默认情况下会：

- 每天凌晨2点（UTC时间）自动执行
- 支持手动触发（在 Actions 页面选择 "rNacos Backup" 工作流并点击 "Run workflow"）

### 3. 备份文件位置

备份文件将保存在以下位置：
- backup分支内：`deploey/backup/nacos/rnacos/backup/`
- 文件命名格式：`rnacos_backup_YYYYMMDD_HHMMSS.data.enc`

## 工作流详解

### 备份流程

1. **下载备份数据**：从通过Secrets配置的rNacos备份API地址下载数据
2. **生成时间戳**：创建带有时间戳的备份文件名
3. **加密文件**：使用 AES-256-CBC 算法加密备份文件
4. **上传到仓库**：将加密后的文件提交到backup分支的指定目录
5. **清理旧备份**：删除超过7天的旧备份文件
6. **保存为工件**：将备份文件保存为 GitHub Actions 工件，保留7天

### 定时设置

当前设置为每天凌晨2点（UTC时间）执行一次。如需修改执行时间，可以编辑 `.github/workflows/rnacos-backup.yml` 文件中的 cron 表达式：

```yaml
schedule:
  - cron: '0 2 * * *'  # 每天凌晨2点执行
```

## 恢复数据

如需恢复数据，请按以下步骤操作：

1. 从仓库下载加密的备份文件
2. 使用 OpenSSL 解密文件：
   ```bash
   openssl enc -d -aes-256-cbc -in rnacos_backup_YYYYMMDD_HHMMSS.data.enc -out rnacos_backup.data -pass pass:你的加密密钥
   ```
3. 将解密后的文件导入到 rNacos 系统中

## 注意事项

- 确保备份 API 地址和令牌的有效性
- 定期检查备份是否成功执行
- 妥善保管加密密钥，避免泄露
- 如需修改备份频率，请编辑 cron 表达式

## 故障排除

如果备份失败，请检查：

1. rNacos 服务是否正常运行
2. API 地址和令牌是否正确
3. GitHub Actions 是否有足够的权限
4. 仓库是否有足够的存储空间

## 自定义配置

如需自定义备份配置，可以修改 `.github/workflows/rnacos-backup.yml` 文件中的以下参数：

- `RNACOS_BACKUP_URL`：通过GitHub Secrets设置rNacos API地址
- `AES_ENCRYPTION_KEY`：通过GitHub Secrets设置加密密钥
- `BACKUP_RETENTION_DAYS`：备份保留天数（当前为7天）
- `ENCRYPTION_ALGORITHM`：加密算法（当前为AES-256-CBC）