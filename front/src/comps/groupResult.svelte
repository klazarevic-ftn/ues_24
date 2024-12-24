<script>
    export let group;
    $: highlights = group.highlights;

    function goToGroup() {
        location.href = `/group/${group.id}`
    }
</script>

<div on:click={goToGroup}>
    {#if highlights.hasOwnProperty("name")}
        {@html highlights.name}
    {:else }
        {group.name}
    {/if}
</div>

<div>
    {#if highlights.hasOwnProperty("description")}
        ...{@html highlights.description}...
    {/if}
</div>

<div style="border-bottom: 2px grey solid;">
    Number of posts: {group.numberOfPosts}
    Average likes: {group.averageLikes}
</div>
