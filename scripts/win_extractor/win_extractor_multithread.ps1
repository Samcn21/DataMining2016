$replaydir = 'C:\Users\remt\mdmi\replays\Heroes of the Storm Europe Heroes Major Go4Heroes Europe\Heroes of the Storm Europe Heroes Major Go4Heroes Europe\'
$files = Get-ChildItem $replaydir
$filecount = (Get-ChildItem $replaydir).Count
$hpdir = 'C:\Users\remt\mdmi\hp\heroprotocol.py'
$outputdir = 'C:\Users\remt\mdmi\out'

$count = 0
ForEach($file in $files) {
    
    $attributeevents = {
        python $hpdir --attributeevents --json $replaydir/$file > $outputdir\attributeevents\$file.json
    }
    $gameevents = {
        python $hpdir --gameevents --json $replaydir/$file > $outputdir\gameevents\$file.json
    }
    $messageevents = {
        python $hpdir --messageevents --json $replaydir/$file > $outputdir\messageevents\$file.json
    }
    $trackerevents = {
        python $hpdir --trackerevents --json $replaydir/$file > $outputdir\trackerevents\$file.json
    }
    $header = {
        python $hpdir --header --json $replaydir/$file > $outputdir\header\$file.json
    }
    $details = {
        python $hpdir --details --json $replaydir/$file > $outputdir\details\$file.json
    }
    $initdata = {
        python $hpdir --initdata --json $replaydir/$file > $outputdir\initdata\$file.json
    }
    
    Start-Job $attributeevents
    Start-Job $gameevents
    Start-Job $messageevents
    Start-Job $trackerevents
    Start-Job $header
    Start-Job $details
    Start-Job $initdata
    
    # Wait for it all to complete
    While (Get-Job -State "Running")
    {
      Start-Sleep 2
    }
    
    $count = $count+1
    Write-Host (($count / $filecount) * 100) % Done
}
