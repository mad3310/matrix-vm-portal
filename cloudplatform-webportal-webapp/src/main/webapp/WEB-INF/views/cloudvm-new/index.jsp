    <!DOCTYPE html>
    <html>
       <head>
            <title></title>
            <link rel="stylesheet" href="/static/stylesheets/bootstrap.css">
            <link rel="stylesheet" href="/static/stylesheets/font-awesome.css">
            <link rel="stylesheet" href="/static/stylesheets/select.css">
            <link rel="stylesheet" href="/static/stylesheets/toaster.css">
            <link rel="stylesheet" href="/static/stylesheets/common.css">
            <link rel="stylesheet" href="/static/stylesheets/style.css">
        </head>
        <body>
            <%@ include file="../../includes/header.jsp"%>
            <div class="main">
                <div ng-include="'/static/apps/cloudvm/partials/sidemenu.html'" class="side-bar"></div>
                <div class="content-wrapper">
                    <div ng-view="ng-view" class="content"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <script type="text/javascript" src="/static/javascripts/require.js" data-main="/static/apps/cloudvm/main.js"></script>
        </body>
    </html>