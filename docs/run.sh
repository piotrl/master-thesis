#!/usr/bin/env bash
#COMPILE=$(xelatex magisterka)
#BIBLIOGRAPHY=$(bibtex magisterka)

rm -rf *.{log,aux,out,lot,lof,ilg,toc,blg,synctex.gz} *~

xelatex magisterka --no-pdf && xelatex magisterka && start ./magisterka.pdf