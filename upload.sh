#!/data/data/com.termux/files/usr/bin/bash
cd "$(dirname "$0")"
pkg install -y git >/dev/null 2>&1
read -p "GitHub username: " U
read -p "Repo name (naya PUBLIC repo): " R
read -s -p "GitHub token: " T; echo
rm -rf .git; git init -q -b main; git add -A
git -c user.name="$U" -c user.email="$U@users.noreply.github.com" commit -qm "dialog"
git remote add origin "https://$U:$T@github.com/$U/$R.git"
git push -u origin main --force && echo "$U/$R" > .repo && git remote set-url origin "https://github.com/$U/$R.git" && echo "Upload ho gaya. 3-5 min baad ./download.sh"
