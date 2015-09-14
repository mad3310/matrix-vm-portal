    <!DOCTYPE html>
    <html>
       <head>
            <title></title>
            <link rel="stylesheet" href="/static/stylesheets/bootstrap.css">
            <link rel="stylesheet" href="/static/stylesheets/font-awesome.css">
            <link rel="stylesheet" href="/static/stylesheets/select.css">
            <link rel="stylesheet" href="/static/stylesheets/toaster.css">
            <link rel="stylesheet" href="/static/stylesheets/rzslider.css">
            <link rel="stylesheet" href="/static/stylesheets/common.css">
            <link rel="stylesheet" href="/static/stylesheets/style.css">
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        </head>
        <body>
            <%@ include file="../../includes/header.jsp"%>
            <div class="main">
                <%@ include file="../../includes/sidebar.jsp"%>
                <div class="content-wrapper">
                    <div ng-view="ng-view" class="content"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <script type="text/javascript" src="/static/javascripts/require.js" data-main="/static/apps/cloudvm/main.js"></script>
        </body>
    </html>