<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <title>Code</title>
</head>
<body>
    <span id="load_date">${code.date}</span>
    <pre id="code_snippet"><code>${code.code}</code></pre>
    <#if (code.time > 0) >
        <span id="time_restriction">${code.time}</span>
    </#if>
    <#if (code.views > 0)>
        <span id="views_restriction">${code.views}</span>
    </#if>
</body>
</html>