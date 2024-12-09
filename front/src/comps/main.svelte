<!-- name, desc, desc from file, # of posts, avg likes -->
<script>
    import Segment from './segment.svelte';
    let options = ['name', 'desc', 'desc from file', '# of posts', 'avg likes'];
</script>

<div class="main column">
    <div class="segment row">
        <input>
        <select>
            {#each options as option}
                <option>{option}</option>
            {/each}
        </select>
        <button>+AND</button>
    </div>
    <button>+OR</button>
</div>

<style>
    .main {

    }

    .segment {

    }

    .row {
        display: flex;
        flex-flow: row nowrap;
    }

    .column {
        display: flex;
        flex-flow: column nowrap;
    }
</style>