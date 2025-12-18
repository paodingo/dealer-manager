@echo off
CHCP 65001 > NUL
echo ==========================================
echo GitHub Repository Push Script
echo ==========================================

set /p username=Enter your GitHub username: 
set /p repository=Enter your GitHub repository name: 

echo.
echo Removing any existing remote origin...
git remote remove origin 2>nul

echo Adding remote repository...
git remote add origin https://github.com/%username%/%repository%.git

echo Setting main branch...
git branch -M main

echo.
echo Pushing code to GitHub...
echo This may take a few minutes depending on your connection speed.
git push -u origin main

if %errorlevel% == 0 (
    echo.
    echo ==========================================
    echo Success! Your project has been pushed to GitHub.
    echo Repository URL: https://github.com/%username%/%repository%
    echo ==========================================
) else (
    echo.
    echo ==========================================
    echo Error occurred while pushing to GitHub.
    echo Please check:
    echo 1. Your internet connection
    echo 2. Your username and repository name
    echo 3. That the repository exists on GitHub
    echo ==========================================
)

pause