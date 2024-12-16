/** @type {import('./$types').PageLoad} */
export function load({ params }) {
    return { type: params.type }
}