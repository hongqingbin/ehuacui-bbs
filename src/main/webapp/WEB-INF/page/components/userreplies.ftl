<#macro userreplies replies>
<table class="table table-striped">
    <#list replies as reply>
        <tr>
            <td>
                ${reply.formatDate(reply.in_time)!}
                回复了
                <a href="/user/${reply.topicAuthor!}">${reply.topicAuthor!}</a>
                创建的话题 › <a href="/topic/${reply.tid!}">${reply.title!}</a>
            </td>
        </tr>
        <tr>
            <td>${reply.marked(reply.content!)}</td>
        </tr>
    </#list>
</table>
</#macro>