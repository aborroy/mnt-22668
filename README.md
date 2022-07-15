# Alfresco MNT-22668 patch

This patch **only** applies to ACS **7.2**, don't deploy it on a different version.

Details can be found in https://github.com/Alfresco/alfresco-community-repo/pull/870

This patch solves an specific issue related with the name of downloaded documents when including special characters (like spaces or parenthesis).

## Building

Regular Maven command can be used.

```
$ mvn clean package -DskipTests
```

This will produce `mnt-22668-1.0.0.amp` file in `target` folder that can be installed in ACS Repo side.

>> AMP package is required due to the use of [Acosix Utility library](https://github.com/Acosix/alfresco-utility)

## Installation

Apply the AMP package in releases to your Alfresco Repository deployment:

https://github.com/aborroy/mnt-22668/releases/tag/1.0.0
