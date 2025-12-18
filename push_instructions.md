# 推送项目到 GitHub 的多种方法

## 方法 1: 标准 HTTPS 方式

```bash
git remote remove origin
git remote add origin https://github.com/用户名/仓库名.git
git branch -M main
git push -u origin main
```

## 方法 2: 使用 FastGit 镜像

```bash
git remote remove origin
git remote add origin https://download.fastgit.org/用户名/仓库名.git
git branch -M main
git push -u origin main
```

推送成功后再改回 GitHub 地址：
```bash
git remote remove origin
git remote add origin https://github.com/用户名/仓库名.git
```

## 方法 3: SSH 方式

1. 生成 SSH 密钥：
```bash
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
```

2. 添加公钥到 GitHub（设置 -> SSH and GPG keys）

3. 使用 SSH 推送：
```bash
git remote remove origin
git remote add origin git@github.com:用户名/仓库名.git
git branch -M main
git push -u origin main
```

## 方法 4: 手动上传

1. 将项目压缩为 ZIP 文件
2. 在 GitHub 上创建仓库时选择 "Import code"
3. 输入 ZIP 文件地址或直接上传

## 方法 5: 使用 GitHub Desktop

1. 下载安装 [GitHub Desktop](https://desktop.github.com/)
2. 登录账户
3. 添加本地仓库
4. 推送到 GitHub

## 网络问题解决

如果遇到网络连接问题，可以尝试配置代理：

```bash
# 设置代理
git config --global http.proxy http://代理地址:端口
git config --global https.proxy https://代理地址:端口

# 取消代理
git config --global --unset http.proxy
git config --global --unset https.proxy
```