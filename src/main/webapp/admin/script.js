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
