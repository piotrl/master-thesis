#!/usr/bin/env bash

rm -rf *.{log,aux,out,lot,lof,ilg,toc,blg,synctex.gz} *~

# -shell-escape
compile="-shell-escape magisterka"

xelatex $compile --no-pdf && bibtex magisterka && xelatex $compile && start ./magisterka.pdf