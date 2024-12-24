<script>
    import { createEventDispatcher } from 'svelte'
    const dispatch = createEventDispatcher()

    export let content;
    export let last;
    export let location;
    export let options;
    let textInputs = ['Name', 'Desc', 'Desc from file','Rules','Title', 'Content','File content','Comments'];
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
        content.query = '';
        content.from = '';
        content.to = '';

        content = content;
    }

    function changeQueryType(event) {
        content.searchType = event.target.value;
        content = content;
    }
</script>

<div class="segment" class:background={hovered}>
    <button disabled={last} on:click={remove} on:mouseenter={flipBackground} on:mouseleave={flipBackground}>X</button>
    <div>
        {#if textInputs.indexOf(content.name) > -1 }
            <input bind:value={content.query}> in
            <select on:change={changeValue}>
                {#each options as option}
                    <option selected={content.name === option ? 'selected' : ''}>{option}</option>
                {/each}
            </select>
            as
            <select on:change={changeQueryType}>
                <option>Phraze</option>
                <option>Fuzzy</option>
            </select> query
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