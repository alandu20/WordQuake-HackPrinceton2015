// function hitbutton() {
//   chrome.tabs.executeScript({
//     file: 'myscript.js'
//   }); 
// }
 
// document.getElementById('buttonid').addEventListener('click', hitbutton);


// Called when the user clicks on the browser action.
// chrome.browserAction.onClicked.addListener(function(tab) {
//   // No tabs or host permissions needed!
//   console.log('Turning ' + tab.url + ' red!');
//   chrome.tabs.executeScript({
//     code: 'document.body.style.backgroundColor="red"'
//   });
// });



chrome.browserAction.onClicked.addListener(function callback(tab){
  chrome.tabs.executeScript(null, {file: "myscript.js"});
});