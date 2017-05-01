#!/usr/bin/env bash

rm -rf *.{log,aux,out,lot,lof,ilg,toc,blg,synctex.gz} *~

xelatex magisterka --no-pdf && bibtex magisterka && xelatex magisterka && start ./magisterka.pdf