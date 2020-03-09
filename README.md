# vcard2clibase

[Clibase](https://www.clibase.com/) is a spam phone calls blocker.  
It is very effective but its UI is quite rudimentary.  
This utility helps with bulk importing contacts into the blocker.  

Use it Unix style:
```bash
cat contacts.vcf | java -DURL_START="http://192.168.1.19/listes.htm?tl=B&act=A&type=B" -DKEY_NAME=nom -DKEY_TEL=num -jar vcard2clibase.jar | xargs -L 1 curl
```
