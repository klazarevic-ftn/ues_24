<script>
    import { createEventDispatcher } from 'svelte'
    const dispatch = createEventDispatcher()

    export let content;
    export let last;
    export let location;

    function remove() {
        let coors = location.split('').map(num => parseInt(num));
        dispatch('remove', {
           first: coors[0], second: coors[1]
        });
    }
</script>

<div class="segment">
    <button disabled={last} on:click={remove}>X</button>
    <div>
        Search <input value={content.name}> in
        <select><option>segment</option></select>
    </div>
</div>

<style>
    .segment {
        display: flex;
        flex-flow: column nowrap;
        padding: 3px;
        gap: 3px;
    }
</style>