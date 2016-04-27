$replaydir = 'C:\Users\remt\mdmi\replays\Heroes of the Storm Europe Heroes Major Go4Heroes Europe\Heroes of the Storm Europe Heroes Major Go4Heroes Europe\'
$files = Get-ChildItem $replaydir
$filecount = (Get-ChildItem $replaydir).Count
$hpdir = 'C:\Users\remt\mdmi\hp\heroprotocol.py'
$outputdir = 'C:\Users\remt\mdmi\out'

$count = 0
ForEach($file in $files) {
    python $hpdir --attributeevents --json $replaydir/$file > $outputdir\attributeevents\$file.json
    python $hpdir --gameevents --json $replaydir/$file > $outputdir\gameevents\$file.json
    python $hpdir --messageevents --json $replaydir/$file > $outputdir\messageevents\$file.json
    python $hpdir --trackerevents --json $replaydir/$file > $outputdir\trackerevents\$file.json
    python $hpdir --header --json $replaydir/$file > $outputdir\header\$file.json
    python $hpdir --details --json $replaydir/$file > $outputdir\details\$file.json
    python $hpdir --initdata --json $replaydir/$file > $outputdir\initdata\$file.json
    $count = $count+1
    Write-Host [int](($count / $filecount) * 100) % Done
}
