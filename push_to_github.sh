#!/bin/bash

# 推送项目到 GitHub 的脚本
echo "请输入您的 GitHub 用户名:"
read username

echo "请输入您的 GitHub 仓库名称（确保已在 GitHub 上创建）:"
read repository

echo "设置远程仓库地址..."
git remote add origin https://github.com/$username/$repository.git

echo "设置主分支..."
git branch -M main

echo "推送代码到 GitHub..."
git push -u origin main

echo "推送完成！"