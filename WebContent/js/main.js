/*
 * jQuery File Upload Plugin JS Example 6.7
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/*jslint nomen: true, unparam: true, regexp: true */
/*global $, window, document */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload();

    // Enable iframe cross-domain access via redirect option:
    $('#fileupload').fileupload(
        'option',
        'redirect',
        window.location.href.replace(
            /\/[^\/]*$/,
            '/cors/result.html?%s'
        )
    );

    if (window.location.hostname === 'localhost:8080') {
        // Demo settings:
        $('#fileupload').fileupload('option', {
            url: 'http://localhost:8080/Quest/imgs/',
            maxFileSize: 5000000,
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png|txt|xls|xlsx)$/i,
            process: [
                {
                    action: 'load',
                    fileTypes: /^image\/(gif|jpeg|png)$/,
                    maxFileSize: 20000000 // 20MB
                },
                {
                    action: 'resize',
                    maxWidth: 1440,
                    maxHeight: 900
                },
                {
                    action: 'save'
                }
            ]
        });
        // Upload server status check for browsers with CORS support:
        if ($.support.cors) {
            $.ajax({
                url: 'http://localhost:8080/Quest/imgs/',
                type: 'HEAD'
            }).fail(function () {
                $('<span class="alert alert-error"/>')
                    .text('Upload server currently unavailable - ' +
                            new Date())
                    .appendTo('#fileupload');
            });
        }
    } else {
        // Load existing files:
        $('#fileupload').each(function () {
            var that = this;
            $.getJSON(this.action, function (result) {
                if (result && result.length) {
                    $(that).fileupload('option', 'done')
                        .call(that, null, {result: result});
                    //console.log(result);
                }
            });
        });
    }

});

function readyFn( jQuery ) {
    // Code to run when the document is ready.
    console.log("start...");
    keySearch();
            var url = "UploadServlet?list=true";
            $.getJSON(url, function (result) {
                if (result && result.length) {
                   // console.log(result)
                    //$(that).fileupload('option', 'done')
                    //    .call(that, null, {result: result});
                    addItems(result);
                }
            });
}
$( document ).ready( readyFn );

function addItems(data){
    var items = [];
  $.each( data, function( key, val ) {
      items.push(item(val));
  });
  $(".table").append(items);
    //console.log(items);
  
}
function addItemsSearch(data){
    var items = [];
  $.each( data, function( key, val ) {
      items.push(item(val));
  });
  $(".table tbody").html(items);
    console.log(items);
  
}
function item(file){
    var obj =
    "<tr class=\"template-download fade in\">"+
            
        "<td class=\"preview\">"+
           " <a href=\"UploadServlet?getfile="+file.name+"\" title=\""+file.name+"\" rel=\"gallery\" download=\""+file.name+"\" data-gallery>"+
               " <img src=\"UploadServlet?getthumb="+file.icon+"\">"+
           " </a>"+
        "</td>"+
        "<td class=\"name\">"+
           " <a href=\"UploadServlet?getfile="+file.name+"\" title=\""+file.name+"\" rel=\"gallery\" download=\""+file.name+"\">"+
                ""+file.name+""+
            "</a>"+
        "</td>"+
        "<td class=\"size\"><span>"+ humanFileSize(file.size,true)+" </span></td>"+
        "<td colspan=\"2\"><a href=\"UploadServlet?getlog="+file.name+"\"  title=\""+file.name+"\" target=\"blank\"><span class=\"fileStatus\">"+file.status+"</span></a> "+" <img src=\"img/"+file.statusIcon+"\">"+"</td>"+

        "<td class=\"delete\">"+
            "<button class=\"btn btn-danger\" data-type=\"GET\" data-url=\"UploadServlet?delfile="+file.name+"\">"+
                "<i class=\"icon-trash icon-white\"></i>"+
                "<span>Delete</span>"+
            "</button>"+
        "<input type=\"checkbox\" name=\"delete\" value=\"1\">"+
        "</td>"+
    "</tr>";
    return obj;
}

function keySearch(){
    $('#keyword').on('input', function() {
        
    // do something
    var url = "UploadServlet?list=true&keyword="+$('#keyword').val();
    console.log(url);
            $.getJSON(url, function (result) {
                if (result && result.length) {
                    //console.log(result)
                    //$(that).fileupload('option', 'done')
                    //    .call(that, null, {result: result});
                    addItemsSearch(result);
                }
            });
    });
}
function humanFileSize(bytes, si) {
    var thresh = si ? 1000 : 1024;
    if(Math.abs(bytes) < thresh) {
        return bytes + ' B';
    }
    var units = si
        ? ['kB','MB','GB','TB','PB','EB','ZB','YB']
        : ['KiB','MiB','GiB','TiB','PiB','EiB','ZiB','YiB'];
    var u = -1;
    do {
        bytes /= thresh;
        ++u;
    } while(Math.abs(bytes) >= thresh && u < units.length - 1);
    return bytes.toFixed(1)+' '+units[u];
}
