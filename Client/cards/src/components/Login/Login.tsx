import { useState } from 'react';
import axios from 'axios';

function Login() {
    let [username, setUsername] = useState("");
    let [password, setPassword] = useState("");

    async function onLoginClicked() {
        let loginDetails = { username, password };
        try {
            let response = await axios.post("http://localhost:8080/users/login", loginDetails);
            let serverResponse = response.data;
            let token = 'Bearer ' + serverResponse;
            axios.defaults.headers.common['Authorization'] = token;
            localStorage.setItem('authToken', token);
        }
        catch (error: any) {
            alert(error.response.data.errorMessage);
        }
    }

    return (
        <div>
            <input type='email' placeholder='Enter your email' onChange={event => setUsername(event.target.value)} />
            <input type='password' placeholder='Password' onChange={event => setPassword(event.target.value)} />
            <button onClick={onLoginClicked} className='LoginButton'>Login</button>
        </div>
    );
}

export default Login;
