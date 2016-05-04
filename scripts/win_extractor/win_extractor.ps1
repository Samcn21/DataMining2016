$replaydir = 'C:\Users\spen.ITU\Desktop\DM2016\Replays\Heroes of the Storm Europe Championship Katowice\Heroes of the Storm Europe Championship Katowice'
$files = Get-ChildItem $replaydir
$filecount = (Get-ChildItem $replaydir).Count
$hpdir = 'C:\Users\spen.ITU\Desktop\DM2016\hp\heroprotocol.py'
$outputdir = 'C:\Users\spen.ITU\Desktop\DM2016\out'

$count = 0
ForEach($file in $files) {
    
    
    <# python.exe $hpdir --attributeevents --json $replaydir/$file > $outputdir\attributeevents\$file.json #>
    <# python.exe $hpdir --gameevents --json $replaydir/$file > $outputdir\gameevents\$file.json #>
    <# python.exe $hpdir --messageevents --json $replaydir/$file > $outputdir\messageevents\$file.json #>
    <# python.exe $hpdir --trackerevents --json $replaydir/$file > $outputdir\trackerevents\$file.json #>
    <# python.exe $hpdir --header --json $replaydir/$file > $outputdir\header\$file.json #>
    python.exe $hpdir --details --json $replaydir/$file > $outputdir\details\$file.json
    $content = Get-Content $outputdir\details\$file.json
    Set-Content -PassThru $outputdir\details\$file.json $content -Encoding ASCII -Force
    <# python.exe $hpdir --initdata --json $replaydir/$file > $outputdir\initdata\$file.json #>
    $count = $count+1
    Write-Host [int](($count / $filecount) * 100) % Done
}
