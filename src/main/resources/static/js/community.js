/**
 * 获取一级评论
 */
function post() {
    var parentId = $("#parent_id").val();
    var content = $("#comment").val();
    comment(parentId,1,content);

}

/**
 * 获取评论列表
 * @param id
 * @param type
 * @param content
 */
function comment(id,type,content)
{
    //对回复类型，进行校验，为空进行提示
    if(!content)
    {
        alert("评论内容不能为空");
        return ;
    }
    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":id,
            "type":type,
            "comment":content
        }),
        success: function (response) {
            if(response.code==200)
            {
                window.location.reload();
            }else
            {
                if(response.code==2003)
                {
                    //没有登录
                    var isLog= confirm(response.message);
                    if(isLog)
                    {
                        window.open("https://github.com/login/oauth/authorize?client_id=c2c9b12364c78c11da16&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    }
                }
                else
                    alert(response.message);
            }

        },
        dataType: "json"
    });
}

/**
 * 开关二级评论
 * @param t
 */
function collapse(t){
    var id=t.getAttribute("data");
    var clazz=$("#cop"+id).attr("class");
    var subCommentContainer = $("#comment" + id);
    if(clazz.indexOf("in")!=-1)
    {
        subCommentContainer.html("");
        $.getJSON("/comment/"+id,function (data) {
            $.each(data.data.reverse(), function (index, comment) {
                var mediaLeftElement = $("<div/>", {
                    "class": "media-left"
                }).append($("<img/>", {
                    "class": "media-object img-rounded",
                    "style":"width: 45px;height: 45px;",
                    "src": comment.user.avatarUrl
                }));
                var mediaBodyElement = $("<div/>", {
                    "class": "media-body"
                }).append($("<h5/>", {
                    "class": "media-heading",
                    "html": comment.user.name
                })).append($("<div/>", {
                    "html": comment.content
                })).append($("<div/>", {
                    "class": "menu"
                }).append($("<span/>", {
                    "class": "pull-right",
                    "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                })));

                var mediaElement = $("<div/>", {
                    "class": "media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                }).append(mediaElement);

                subCommentContainer.prepend(commentElement);
            });
        });
    }
    t.classList.toggle("active");
    //toggleClass 回去判断 class 中有没有 in 没有的话个给加上 in 有就去掉
    $("#cop"+id).toggleClass('in');

}

/**
 * 二级评论
 */
function comment2(t) {
    var id=t.getAttribute("data");
    var content = $("#comment2"+id).val();
    console.log(content);
    comment(id,2,content);
}