@echo off
echo Uninstalling registry entries
REG DELETE HKEY_CLASSES_ROOT\*\shell\Sincronizar legenda
REG DELETE HKEY_CLASSES_ROOT\Folder\shell\Sincronizar legenda
echo Uninstalling successfull