function loadFile() {
    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (e) {
            const fileContent = e.target.result;
            const transformedContent = transformContent(fileContent);
            displayTransformedContent(transformedContent);
        };

        reader.readAsText(file);
    }
}

function transformContent(content) {
    // Perform the desired transformation on the content
    // For example, let's capitalize all the text
    return content.toUpperCase();
}

function displayTransformedContent(content) {
    const transformedContentTextarea = document.getElementById("transformedContent");
    transformedContentTextarea.value = content;
}




function loadXML() {
    var fileInput = document.getElementById('xmlFileInput');
    var file = fileInput.files[0];
    var reader = new FileReader();
  
    reader.onload = function(e) {
      var xmlContent = e.target.result;
      document.getElementById('xmlContent').value = xmlContent;
  
      var parser = new DOMParser();
      var xmlDoc = parser.parseFromString(xmlContent, 'text/xml');
    
    };
  
    reader.readAsText(file);
  }
  


  function loadXMLOLd() {
    var fileInput = document.getElementById('xmlFileInput');
    var file = fileInput.files[0];
    var reader = new FileReader();
  
    reader.onload = function(e) {
      var xmlContent = e.target.result;
      document.getElementById('xmlContent').value = xmlContent;
  
      var parser = new DOMParser();
      var xmlDoc = parser.parseFromString(xmlContent, 'text/xml');
      
      var jsonString = JSON.stringify(xmlToJson(xmlDoc), null, 2);
      document.getElementById('jsonContent').value = jsonString;
    };
  
    reader.readAsText(file);
  }


  function transformOLD() {
    
      var parser = new DOMParser();
      var xmlDoc = parser.parseFromString(document.getElementById('xmlContent').value, 'text/xml');
      
      var jsonString = JSON.stringify(xmlToJson(xmlDoc), null, 2);
      document.getElementById('jsonContent').value = jsonString;
  }




  function transform2() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8282/ontologymanager/biecointerface/', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', 'qJACs1J0apruOOJCg');

    
    xhr.onload = function () {
      var status;
      if (xhr.status === 200) {
        status.textContent = 'Upload successful!';
        
        const response = JSON.parse(xhr.responseText);

        const result = xhr.responseText; // Assuming the server response has a 'result' property response.text()
        
        console.log(result);

        const textarea = document.getElementById('jsonContent');
        textarea.value = result;
      } else {
        status.textContent = 'Upload failed: ' + xhr.status;
      }
    };
    const data = {
      jobID: '1234',
      timestamp: '',
      messageType: 'Data',
      sourceID: '4',
      event: {
        OntologyRequest: {
          operationName: 'parseAMADEOSSoSProfileNew',
          ontologyContent: document.getElementById('xmlContent').value,
        },
      },
      crc: 1234567,
    };
    xhr.send(JSON.stringify(data));
  }
  














  function xmlToJson(xml) {
    var obj = {};
  
    if (xml.nodeType === 1) {
      if (xml.attributes.length > 0) {
        obj['@attributes'] = {};
        for (var j = 0; j < xml.attributes.length; j++) {
          var attribute = xml.attributes.item(j);
          obj['@attributes'][attribute.nodeName] = attribute.nodeValue;
        }
      }
    } else if (xml.nodeType === 3) {
      obj = xml.nodeValue.trim();
    }
  
    if (xml.hasChildNodes()) {
      for (var i = 0; i < xml.childNodes.length; i++) {
        var item = xml.childNodes.item(i);
        var nodeName = item.nodeName;
  
        if (typeof obj[nodeName] === 'undefined') {
          obj[nodeName] = xmlToJson(item);
        } else {
          if (typeof obj[nodeName].push === 'undefined') {
            var old = obj[nodeName];
            obj[nodeName] = [];
            obj[nodeName].push(old);
          }
          obj[nodeName].push(xmlToJson(item));
        }
      }
    }
  
    return obj;
  }