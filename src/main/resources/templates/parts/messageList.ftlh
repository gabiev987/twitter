<#include "security.ftlh">
<#import "pager.ftlh" as p>
<#import "messageCard.ftlh" as m>

<@p.pager currentUrl page />

<div class="card-columns" id="message-list">
    <#list page.content as message>
        <@m.messageCard currentUrl message currentUserId/>
    <#else>
        No message
    </#list>
</div>