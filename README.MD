# やるべきこと
- supabaseの詳細な認証情報はlocal.propertiesにおいてください。
- パッケージ名を変更したいときは以下の手順に沿ってください。[参考](https://zenn.dev/squ4t4r014/articles/897a776393bbb4)

```
手順
変更前の名前を(旧○○)、変更後の名前を(新○○)と表記します。
また、一応プロジェクトのバックアップは取ってください。

1. Android Studioの左側にあるディレクトリツリー(Project)から「app/src/main/java/(旧パッケージ名)」を開きます。
2. パッケージ名のディレクトリを右クリックして「Refactor」→「Rename...」をクリックします。複数のディレクトリに影響が出る趣旨のWarningが出ますので「Rename package」をクリックします。
3. 変更したいパッケージ名に書き換え、「Refactor」をクリックします。
4. 「Edit」→「Find」→「Replace in Path...」(Shift+Ctrl+R)(Shift+Command+R)を開きます。
5. オプション「大文字と小文字を区別する」を有効にして、旧パッケージ名を新パッケージ名でReplaceします。
6. オプション「大文字と小文字を区別する」を有効にして、旧アプリ名を新アプリ名でReplaceします。
(6.でReplaceされなかった場合)「.idea/name」を開き、アプリ名を変更します。
7. 「.idea/modules/(旧アプリ名).iml」を「(新アプリ名).iml」へ名前を変更します。
8. 「.idea/modules/app/(旧アプリ名).app.iml」を「(新アプリ名).app.iml」へ名前を変更します。
9. 「Build」→「Clean Project」をクリックします。
10. Android Studioを閉じ、プロジェクトのルートディレクトリの名前を新プロジェクト名に変更します。
11. Android EmulatorやAndroid実機等に残っている旧アプリを削除します。
12. (必要であれば)Gitの設定を変更します。
```

# 
