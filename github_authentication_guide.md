# GitHub 身份验证指南

在推送到 GitHub 时，身份验证是必需的。如果没有正确配置身份验证，推送将会失败。

## 身份验证方式

### 1. Personal Access Token (推荐)

#### 创建 Personal Access Token:
1. 登录 GitHub
2. 点击右上角头像，选择 "Settings"
3. 在左侧菜单中选择 "Developer settings"
4. 选择 "Personal access tokens" > "Tokens (classic)"
5. 点击 "Generate new token" > "Generate new token (classic)"
6. 给令牌起个名字（如 "git-push-access"）
7. 选择权限范围，至少选择 `repo`（完全控制私人仓库）
8. 点击 "Generate token"
9. **重要**：立即复制生成的令牌，离开页面后将无法再次查看

#### 使用 Personal Access Token:
当 Git 提示输入密码时，输入您的 Personal Access Token 而不是 GitHub 账户密码。

### 2. 在 URL 中嵌入凭据

您可以将用户名和令牌嵌入到远程仓库 URL 中：
```bash
git remote remove origin
git remote add origin https://用户名:个人访问令牌@github.com/用户名/仓库名.git
git push origin main
```

### 3. 使用凭据助手

Git 提供了几种凭据助手来存储您的凭据：

#### 存储在磁盘上（明文存储）：
```bash
git config --global credential.helper store
```

#### 存储在内存中（Windows 凭据管理器）：
```bash
git config --global credential.helper wincred
```

配置后，第一次推送时输入用户名和令牌，之后 Git 会自动使用它们。

### 4. SSH 密钥方式（无需密码）

#### 生成 SSH 密钥：
```bash
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
```

#### 启动 ssh-agent：
```bash
eval "$(ssh-agent -s)"
```

#### 添加密钥到 ssh-agent：
```bash
ssh-add ~/.ssh/id_rsa
```

#### 将公钥添加到 GitHub：
1. 复制公钥内容：
   ```bash
   type ~/.ssh/id_rsa.pub
   ```
2. 在 GitHub 上进入 Settings > SSH and GPG keys
3. 点击 "New SSH key"
4. 粘贴公钥内容并保存

#### 使用 SSH URL：
```bash
git remote remove origin
git remote add origin git@github.com:用户名/仓库名.git
git push origin main
```

## 故障排除

### 凭据错误
如果更改了密码或令牌，您可能需要清除旧的凭据：
```bash
git config --global --unset credential.helper
```
或者删除存储的凭据文件。

### 验证凭据是否正确
您可以手动测试凭据：
```bash
curl -u 用户名:令牌 https://api.github.com/user
```
如果返回用户信息，则凭据有效。

## 最佳实践

1. 使用 Personal Access Token 而不是账户密码
2. 为不同的用途创建不同的令牌，并设置适当的权限和过期时间
3. 定期轮换令牌
4. 对于开源项目，考虑使用 SSH 密钥方式