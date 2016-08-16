module.exports = { 
  init: function(options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "fox", "init", [options]);
  },   
  sendLtvConversion: function(options, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "fox", "sendLtvConversion", [options]);
  }   
};
