<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="/css/style.css" rel="stylesheet">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <title>Latest</title>
</head>
<body>
    <#list codes as code>
        <span id="load_date">${code.date}</span>
        <pre id="code_snippet"><code>${code.code}</code></pre>
    </#list>
</body>
</html>