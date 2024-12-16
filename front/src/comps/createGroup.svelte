<script>
    let name = '';
    let description = '';
    let rules = '';
    let fileEl;
    async function createGroup() {
        let formData = new FormData();
        formData.append('group', new Blob([JSON.stringify({name, description, rules})], {type: 'application/json'}));
        formData.append('file', fileEl.files[0]);

        try {
            const response = await fetch('http://localhost:8080/group', {
                headers: {
                    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Headers': '*',
                },
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                const result = await response.text();
                location.href = `/group/${result}`;
            } else {
                const error = await response.text();
                alert("error");
                console.log(error);
            }
        } catch (error) {
            console.log(error);
        }
    }
</script>
<div class="main">
    <div class="row">
        <label for="name">Name</label><input name="name" bind:value={name}>
    </div>
    <div class="row">
        <label for="desc">Desc.</label><textarea name="desc" bind:value={description}></textarea>
    </div>
    <div class="row">
        <label for="file">Desc. pdf</label><input type="file" name="file" accept="application/pdf" bind:this={fileEl}>
    </div>
    <div class="row">
        <label for="rules">Rules</label><textarea name="rules" bind:value={rules}></textarea>
    </div>
    <button on:click={createGroup}>Create group</button>
</div>

<style>
    .main {
        display: flex;
        flex-flow: column nowrap;
        width: 33vw;
        justify-content: center;
        gap: 5px;
    }
    .main button {
        width: 33%;
    }

    .row input, .row textarea {
        width: 100%;
    }
</style>