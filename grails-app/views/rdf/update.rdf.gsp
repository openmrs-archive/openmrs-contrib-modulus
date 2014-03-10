<updates configVersion="1.1" moduleId="${module.legacyId ?: module.slug}">
    <g:each in="${module.releases.reverse()}">
        <update>
            <currentVersion>${it.moduleVersion}</currentVersion>
            <requireOpenMRSVersion>${it.requiredOMRSVersion}</requireOpenMRSVersion>
            <downloadURL>${it.downloadURL}</downloadURL>
        </update>
    </g:each>
</updates>