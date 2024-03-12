import { useEffect, useState } from 'react'

const TestPage = () => {
    const [data, setData] = useState();

    useEffect(() => {
        fetch('http://localhost:8080/test')
        .then(response => response.text())
        .then(data => setData(data))
        .catch(error => console.log(error));
    }, []);

    return (
        <div>
            <h1>Test Page</h1>
            <p>{ data }</p>
        </div>
    )
}

export default TestPage
