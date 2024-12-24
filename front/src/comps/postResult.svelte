<script>
    export let post;
    $: highlights = post.highlights;

    async function goToPost() {
        const response = await fetch(`http://localhost:8080/post/group/${post.id}`, {
            headers: {
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Headers': '*',
            },
            method: 'GET'
        });

        if (response.ok) {
            const result = await response.text();
            location.href = `/group/${result}/post/${post.id}`;
        } else {
            const error = await response.text();
            // console.log(error);
        }
        // location.href = `/post/${post.id}`;
    }
</script>

<div on:click={goToPost}>
    {#if highlights.hasOwnProperty("title")}
        {@html highlights.title}
    {:else }
        {post.title}
    {/if}
</div>

<div >
    {#if highlights.hasOwnProperty("fullContent")}
    ...{@html highlights.fullContent}...
    {:else }
        {post.fullContent}
    {/if}
</div>

<div style="border-bottom: 2px grey solid;">
    {#if highlights.hasOwnProperty("commentContent")}
        ...{@html highlights.commentContent}...
    {/if}
</div>