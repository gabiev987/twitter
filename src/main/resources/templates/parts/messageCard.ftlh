<#macro messageCard currentUrl message currentUserId>

    <div class="card my-3" data-id="${message.id}">
        <#if message.filename??>
            <img src="/img/${message.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <span>${message.text}</span><br/>

            <i>#${message.tag}</i>
        </div>
        <div class="card-footer text-muted container">
            <div class="row">
                <a class="col align-self-center" href="/user-messages/${message.author.id}">${message.authorName}</a>
                <a class="col align-self-center" href="/messages/${message.id}/like">
                    <#if message.meLiked>
                        <i class="fas fa-heart"></i>
                    <#else>
                        <i class="far fa-heart"></i>
                    </#if>
                    ${message.likes}
                </a>
                <#if message.author.id == currentUserId>
                    <a class="col btn btn-primary" href="/message?messageId=${message.id}&redirectUrl=${currentUrl}">
                        Edit
                    </a>
                </#if>
            </div>
        </div>
    </div>

</#macro>