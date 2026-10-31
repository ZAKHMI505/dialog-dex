#!/data/data/com.termux/files/usr/bin/bash
cd "$(dirname "$0")"
R=$(cat .repo) || { echo "Pehle ./upload.sh"; exit 1; }
curl -fL -o ~/DarkGPT_Dialog_HOOK.zip "https://github.com/$R/releases/download/latest/DarkGPT_Dialog_HOOK.zip" || { echo "Abhi ready nahi. Repo ke Actions tab me hari tick ka wait karo."; exit 1; }
cp ~/DarkGPT_Dialog_HOOK.zip /storage/emulated/0/Download/ && echo "ZIP Download folder me aa gayi: DarkGPT_Dialog_HOOK.zip"
