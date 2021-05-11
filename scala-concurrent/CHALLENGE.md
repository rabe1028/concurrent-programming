# 挑戦課題

## 4-2

スレッドダンプが見られることによる悪影響はほぼないと思うが、不安なので、上げていない。
以下にコマンドを記載。

```sh
$ jps
752
1505 NailgunRunner
1042
1588 Application
2279 Jps
2057 Launcher
2026 MainGenericRunner
$ jstack 2026
...
```