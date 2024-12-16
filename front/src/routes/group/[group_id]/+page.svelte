<script>
    let { data } = $props();
    let group = JSON.parse(data.group);
    let nameEl, descEl, fileEl, rulesEl, titleEl, contentEl, postFileEl;

    async function updateGroup() {
        let formData = new FormData();
        formData.append('dto', new Blob([JSON.stringify({name: nameEl.value, description: descEl.value, rules: rulesEl.value})], {type: 'application/json'}));
        if(fileEl.files[0])
            formData.append('file', fileEl.files[0]);

        try {
            const response = await fetch(`http://localhost:8080/group/${group.id}`, {
                headers: {
                    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Headers': '*',
                },
                method: 'PUT',
                body: formData
            });

            if (response.ok) {
                const result = await response.text();
                alert('Success: ' + result);
            } else {
                const error = await response.text();
                alert('Error: ' + error);
            }
        } catch (error) {
            console.log(error);
        }
    }

    async function deleteGroup() {
        try {
            const response = await fetch(`http://localhost:8080/group/${group.id}`, {
                headers: {
                    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH, OPTIONS',
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Headers': '*',
                },
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Success.');
            } else {
                const error = await response.text();
                alert('Error: ' + error);
            }
        } catch (error) {
            console.log(error);
        }

        location.href = "/";
    }

    async function createPost() {
        let formData = new FormData();
        formData.append('post', new Blob([JSON.stringify({groupId:group.id, title:titleEl.value, content:contentEl.value})], {type: 'application/json'}));
        formData.append('file', postFileEl.files[0]);

        try {
            const response = await fetch('http://localhost:8080/post', {
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
                location.href = `/group/${group.id}/post/${result}`;
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
        <label for="name">Name</label><input name="name" value={group.name} bind:this={nameEl}>
    </div>
    <div class="row">
        <label for="desc">Desc.</label><textarea name="desc" bind:this={descEl}>{group.description}</textarea>
    </div>
    <div class="row">
        <label for="file">Desc. pdf</label><input type="file" name="file" accept="application/pdf" bind:this={fileEl}>
    </div>
    <div class="row">
        <label for="rules">Rules</label><textarea name="rules" bind:this={rulesEl}>{group.rules}</textarea>
    </div>
    <button on:click={updateGroup}>Update group</button>
    <button on:click={deleteGroup}>Delete group</button>
</div>

<hr style="border-top: 5px solid #bbb; width: 100%;">

<div class="main">
    <div class="row">
        <label for="title">Title</label><input name="title" type="text" bind:this={titleEl}/>
    </div>

    <textarea name="content" bind:this={contentEl}></textarea>

    <div class="row">
        <label for="file">Desc. pdf</label><input type="file" name="file" accept="application/pdf" bind:this={postFileEl}>
    </div>

    <button on:click={createPost}>Post</button>
</div>

<style>
    .main {
        display: flex;
        flex-flow: column nowrap;
        width: 33vw;
        justify-content: center;
        gap: 5px;
    }

    .row input, .row textarea {
        width: 100%;
    }
</style>