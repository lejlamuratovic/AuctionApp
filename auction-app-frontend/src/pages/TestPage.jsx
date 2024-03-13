import { useEffect, useState } from 'react'
import { BASE_URL } from '../constants';

const TestPage = () => {
    const [data, setData] = useState();

    useEffect(() => {
        fetch(`${BASE_URL}v1/test/`)
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
