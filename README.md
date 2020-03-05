# vcard2clibase

[Clibase](https://www.clibase.com/) is a spam phone calls blocker.  
It is very effective but its UI is quite rudimentary.  
This utility helps with bulk importing contacts into the blocker.  

Use it Unix style:
```bash
cat ~/Downloads/contacts.vcf | java -DURL_START="http://clibasehost/toto?aa=bb" -DKEY_NAME=NOM -DKEY_TEL=TELEPHON -jar target/vcard2clibase.jar | xargs -L 1 curl
```
