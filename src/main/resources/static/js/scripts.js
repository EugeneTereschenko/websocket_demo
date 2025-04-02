function setCookie(name, value, days) {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function getCookie(name) {
    const nameEQ = name + "=";
    const ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function scrollToBottom() {
    const messageContainer = document.querySelector('.custom-background-message');
    messageContainer.scrollTop = messageContainer.scrollHeight;
}

function saveUsername() {
    const username = document.getElementById("username").value;
    setCookie("username", username, 7);
}

function loadUsername() {
    const username = getCookie("username");
    if (username) {
        document.getElementById("message-user").value = username;
    }
}

function fetchMessages() {
    fetch('/messages')
        .then(response => response.json())
        .then(data => {
            const messagesContainer = document.querySelector('.custom-background-message ul');
            messagesContainer.innerHTML = '';
            data.forEach(message => {
                const messageElement = document.createElement('li');
                messageElement.innerHTML = `<p>${message.user}</p><p>${message.message}</p>`;
                messagesContainer.appendChild(messageElement);
                scrollToBottom();
            });
        })
        .catch(error => console.error('Error fetching messages:', error));
}

window.onload = function() {
    loadUsername();
    scrollToBottom();
    setInterval(fetchMessages, 5000);
}