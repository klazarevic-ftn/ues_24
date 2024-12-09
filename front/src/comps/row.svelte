<script>
    import { createEventDispatcher } from 'svelte'
    const dispatch = createEventDispatcher()

    import Segment from "./segment.svelte";

    export let row;
    export let index;
    let hovered = false;

    function removeSegment(event) {
        dispatch('remove', event.detail);
    }

    function addAnd() {
        dispatch('addand', { index: index });
    }

    function removeRow() {
        dispatch('removerow', { index: index });
    }

    function flipBackground() {
        hovered = !hovered;
    }
</script>

<div class="row" style="gap: 5px; padding: 5px;" class:background={hovered}>
    {#each row as segment}
        <Segment on:remove={removeSegment} content={segment} last={index === 0 && row.indexOf(segment) === 0} location={`${index}${row.indexOf(segment)}`}/>

        {#if row.indexOf(segment) + 1 === row.length && row.length < 5}
            <button on:click={addAnd} data-id={index}>+AND</button>
        {:else if row.length > 1 && row.indexOf(segment) < 4}
            <span style="display: flex; align-items: end; padding: 0 5px; font-weight: bold;">AND</span>
        {/if}
    {/each}

    {#if index}
        <button on:click={removeRow} data-id={index} on:mouseenter={flipBackground} on:mouseleave={flipBackground}>X</button>
    {/if}
</div>

<style>
    .row {
        display: flex;
        flex-flow: row nowrap;
    }

    .background {
        background: indianred;
    }
</style>