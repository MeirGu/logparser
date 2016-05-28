<!DOCTYPE html>

<html lang="en">

<body>
<h1>Report</h1>

<form name="model" method="post" action="count">
    <input type="text" name="field" id="field" value="${search}"/>
    <input type="submit" value="Count">
</form>

<#if values??>
    <#list values>
        <ul>
            <#items as value>
                <li><#if value.date??>${value.date}</#if>
                    <#if value.time??>${value.time}</#if>
                    <#if value.timeTaken??>${value.timeTaken}</#if>
                    <#if value.cIp??>${value.cIp}</#if>
                    <#if value.csUsername??>${value.csUsername}</#if>
                    <#if value.csAuthGroup??>${value.csAuthGroup}</#if>
                    <#if value.xExceptionId??>${value.xExceptionId}</#if>
                    <#if value.scFilterResult??>${value.scFilterResult}</#if>
                    <#if value.csCategories??>${value.csCategories}</#if>
                    <#if value.csReferer??>${value.csReferer}</#if>
                    <#if value.scStatus??>${value.scStatus}</#if>
                    <#if value.sAction??>${value.sAction}</#if>
                    <#if value.csMethod??>${value.csMethod}</#if>
                    <#if value.rsContentType??>${value.rsContentType}</#if>
                    <#if value.csUriScheme??>${value.csUriScheme}</#if>
                    <#if value.csHost??>${value.csHost}</#if>
                    <#if value.csuriPort??>${value.csuriPort}</#if>
                    <#if value.csUriPath??>${value.csUriPath}</#if>
                    <#if value.csUriQuery??>${value.csUriQuery}</#if>
                    <#if value.csUriExtension??>${value.csUriExtension}</#if>
                    <#if value.csUserAgent??>${value.csUserAgent}</#if>
                    <#if value.sIp??>${value.sIp}</#if>
                    <#if value.scBytes??>${value.scBytes}</#if>
                    <#if value.csBytes??>${value.csBytes}</#if>
                    <#if value.xVirusId??>${value.xVirusId}</#if>
                    - ${value.count}</li>
            </#items>
        </ul>
    </#list>
</#if>

<br/><br/>
<a href="/log/load">>>> Load log <<<</a>
</body>

</html>