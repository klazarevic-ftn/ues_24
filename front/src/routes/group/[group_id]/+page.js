/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
    try {
        const response = await fetch(`http://localhost:8080/group/${params.group_id}`, {
            headers: {
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': '*',
            },
            method: 'GET'
        });

        if (response.ok) {
            const result = await response.text();
            return { group: result }
        } else {
            const error = await response.text();
            // console.log(error);
        }
    } catch (error) {
        console.log(error);
    }
}