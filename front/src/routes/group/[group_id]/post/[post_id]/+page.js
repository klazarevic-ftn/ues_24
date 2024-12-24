/** @type {import('./$types').PageLoad} */
export async function load({ params }) {

    try {
        const response = await fetch(`http://localhost:8080/post/${params.post_id}`, {
            headers: {
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': '*',
            },
            method: 'GET'
        });

        if (response.ok) {
            const result = await response.text();
            console.log(result);
            return { post: result, groupId: params.group_id }
        } else {
            const error = await response.text();
            // console.log(error);
        }
    } catch (error) {
        console.log(error);
    }
}