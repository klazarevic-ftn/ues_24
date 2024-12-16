<script>
    import { createEventDispatcher } from 'svelte'
    const dispatch = createEventDispatcher()

    export let content;
    export let last;
    export let location;

    let options = ['Name', 'Desc', 'Desc from file', '# of posts', 'Avg. likes'];
    let hovered = false;

    function remove() {
        let coors = location.split('').map(num => parseInt(num));
        dispatch('remove', {
           queryIndex: coors[0], segmentIndex: coors[1]
        });
    }

    function flipBackground() {
        if(!last)
            hovered = !hovered;
    }

    function changeValue(event) {
        content.name = event.target.value;
        content = content;
    }
</script>

<div class="segment" class:background={hovered}>
    <button disabled={last} on:click={remove} on:mouseenter={flipBackground} on:mouseleave={flipBackground}>X</button>
    <div>
        {#if content.name === 'Name' || content.name === 'Desc' || content.name === 'Desc from file'}
            <input bind:value={content.query}> in
            <select on:change={changeValue}>
                {#each options as option}
                    <option selected={content.name === option ? 'selected' : ''}>{option}</option>
                {/each}
            </select>
        {:else}
            <select on:change={changeValue}>
                {#each options as option}
                    <option selected={content.name === option ? 'selected' : ''}>{option}</option>
                {/each}
            </select> is from <input class='number' type="number" bind:value={content.from}> to <input class='number' type="number" bind:value={content.to}>
        {/if}

    </div>
</div>

<style>
    .segment {
        display: flex;
        flex-flow: column nowrap;
        padding: 3px;
        gap: 3px;
    }

    .background {
        background: indianred;
    }

    .number {
        width: 50px;
    }
</style>