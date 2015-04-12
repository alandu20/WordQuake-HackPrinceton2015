var SENDTOPHP = "";
var id = " 9364921 ";

walk(document.body);
handleText(SENDTOPHP);
var oldArr = SENDTOPHP.split(id);

// to replace text NOT in html attributes [duplicate]
function walk(node) {
  var child, next;

  switch (node.nodeType) {
    case 1:  // Element
    case 9:  // Document
    case 11: // Document fragment
   child = node.firstChild;
   while (child) {
    next = child.nextSibling;
    walk(child);
    child = next;
  }
      break;

    case 3: // Text node
      if (node.parentElement.tagName.toLowerCase() !== "script" && node.nodeValue.trim().length > 0) { //&& node.parentElement.attributes !== "id" //.replace(/ /g,'')

        var ascii = /^[ -~\t\n\r]+$/;

        if ( !ascii.test( node.nodeValue ) ) {

          var nodeStr = "";
          for (var k = 0; k < node.nodeValue.length; k++)
          {
            if ( !ascii.test( node.nodeValue.charAt(k) ) ) { 
              nodeStr = nodeStr + ' ';
              
            }
            else
              nodeStr = nodeStr + node.nodeValue.charAt(k);


          }
          SENDTOPHP = SENDTOPHP + nodeStr + id;
        }
        else {

          SENDTOPHP = SENDTOPHP + node.nodeValue + id;
        }
      }
      break;
    }
  }

  function walkagain(node, arr) {
  var child, next;

  switch (node.nodeType) {
    case 1:  // Element
    case 9:  // Document
    case 11: // Document fragment
   child = node.firstChild;
   while (child) {
    next = child.nextSibling;
    walkagain(child, arr);
    child = next;
  }
      break;

    case 3: // Text node
      if (node.parentElement.tagName.toLowerCase() !== "script" && node.nodeValue.trim().length > 0) {
        var i = oldArr.indexOf(node.nodeValue);

        if (arr[i])
          node.nodeValue = arr[i];
      }
      break;
    }
}

function handleText(bigtext) {
  getTextArray(bigtext, function(data) {
    walkagain(document.body, data);
  });
}

function getTextArray(text, cb) {
  if (!text) throw new Error('Page data was undefined.');

  var xhr = new XMLHttpRequest();
  var data = new FormData();

  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        var v = xhr.responseText;

        v = v.replace(/<br\s*\/?>/mg,'');

        var newarray = v.split(id);
        return cb(newarray);
      }
      else {
        throw new Error('Posting to server failed');
      }
    }
  }

  data.append("data" , text);
  xhr.open("POST", "https://wordquake.me/java/javaExecution.php", true);
  xhr.send(data);
}