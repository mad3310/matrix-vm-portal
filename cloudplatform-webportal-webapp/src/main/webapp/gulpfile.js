var gulp = require('gulp');
// 组件引入
var htmlmin=require('gulp-htmlmin'),//- html压缩
minifyCss = require('gulp-minify-css');//- CSS压缩
uglify = require('gulp-uglify'),//- js压缩
concat = require('gulp-concat'),//- 多个文件合并为一个；
rename=require('gulp-rename'),
rev = require('gulp-rev'),//- 对文件名加MD5后缀
revCollector = require('gulp-rev-collector');//- 路径替换
notify = require('gulp-notify'),//提示信息
// 压缩html
gulp.task('html', function(){
  return gulp.src('./home/*.html')
    .pipe(htmlmin({collapseWhitespace: true}))
    .pipe(gulp.dest('./dest/html/home'))
    .pipe(notify({ message: 'html task ok' }));
 
});
// 压缩、重命名静态页css
gulp.task('homecss', function() {
  return gulp.src(['./static/staticPage/css/common.css','./static/staticPage/css/style.css','./static/staticPage/css/toastr.css'])//- 需要处理的css文件，放到一个字符串数组里
        .pipe(minifyCss())//- 压缩处理成一行
        .pipe(rev())//- 文件名加MD5后缀
        .pipe(gulp.dest('./dest/css/staticPage'))//- 输出文件本地
        .pipe(rev.manifest({path:'homecss.json'}))//- 生成一个rev-manifest.json
        .pipe(gulp.dest('./rev'))
        .pipe(notify({ message: 'homecss task ok' }));
});
//压缩、合并控制台css
gulp.task('consolecmcss', function() {
  return gulp.src(['./static/stylesheets/bootstrap.css','./static/stylesheets/font-awesome.css','./static/stylesheets/toaster.css','./static/stylesheets/rzslider.css',
    './static/stylesheets/common.css','./static/stylesheets/style-cloudvm.css','./static/stylesheets/style-profile.css'])//- 需要处理的css文件，放到一个字符串数组里
        .pipe(minifyCss())//- 压缩处理成一行
        .pipe(rev())//- 文件名加MD5后缀
        .pipe(gulp.dest('./dest/css/stylesheets'))//- 输出文件本地
        .pipe(rev.manifest({path:'consolecmcss.json'}))//- 生成一个rev-manifest.json
        .pipe(gulp.dest('./rev/stylesheets'))
        .pipe(notify({ message: 'consolecmcss task ok' }));
});
// 压缩js文件
gulp.task('js', function() {
  return gulp.src(['./static/staticPage/js/browserCheck.js','./static/staticPage/js/home.js','./static/staticPage/js/toastr.js','./static/page-js/payment/payment.js','./static/page-js/invite/inviteCode.js'])
    .pipe(uglify())//js压缩
    .pipe(rev())//- 文件名加MD5后缀
    .pipe(gulp.dest('./dest/js/staticPage'))
    .pipe(rev.manifest({path:'js.json'}))//- 生成一个rev-manifest.json
    .pipe(gulp.dest('./rev'))
    .pipe(notify({ message: 'js task ok' }));
});
gulp.task('rev', function() {
    return gulp.src(['./rev/*.json', './home/*.html','./WEB-INF/views/payment/*.jsp','./WEB-INF/views/invite/*.jsp'])//- 读取 rev-manifest.json 文件以及需要进行css名替换的文件
        .pipe(revCollector())//- 执行文件内键值对进行替换
        .pipe(gulp.dest('./dest/html/home'))//- 替换后的文件输出的目录
        .pipe(notify({ message: 'rev task ok' }));
});
gulp.task('consolerev', function() {
    return gulp.src(['./rev/stylesheets/*.json', './WEB-INF/views/profile.jsp','./WEB-INF/views/cloudvm-new/index.jsp'])//- 读取 rev-manifest.json 文件以及需要进行css名替换的文件
        .pipe(revCollector())//- 执行文件内键值对进行替换
        .pipe(gulp.dest('./dest/jsp/'))//- 替换后的文件输出的目录
        .pipe(notify({ message: 'consolerev task ok' }));
});

// gulp.task('default', ['homecss','consolecmcss','js','rev','consolerev']);
gulp.task('default', ['rev']);