# Translations

## Overview

Brief description of the translation/internationalization (i18n) system from backend perspective including supported
languages, file structure, and usage instructions.

## Supported Languages

| Code | Language | Status         |
|------|----------|----------------|
| en   | English  | 🔄 In Progress |
| pl   | Polish   | 🔄 In Progress |

## File Structure

```
/translations
├── en/
│   └── common.json
└── pl/
    └── common.json
```

## Translation File

Translation file will contain entries in nested, i18n structure as follows:

```json
{
  "edit": {
    "title": "Edit Item",
    "description": "Modify the details of your item here."
  }
}
```

### Common Keys

| Key              | EN                                    | PL                                        |
|------------------|---------------------------------------|-------------------------------------------|
| edit.title       | Edit Item                             | Edytuj przedmiot                          |
| edit.description | Modify the details of your item here. | Zmień szczegóły swojego przedmiotu tutaj. |

## Usage

```typescript
// Example usage
import {t} from "i18n";

const message = t("edit.title");
```

## Translation Management

Front-end developers can manage translations through the script that will pull that translation file from the backend
resources directory:

```bash
npm run pull-translations -- --lang=pl --app=common
```

## Adding New Translations

1. Provide the key path (i.e. "edit.title")
2. Provide the translation for the target language (pl) with application name (default is "common")
3. Pull matching translation file and verify if the key exists
4. Update the respective JSON file in the translations directory

## Deleting Translations

1. Provide the key path (i.e. "edit.title")
2. Provide the target language (pl) with application name (default is "common")
3. Pull matching translation file and verify if the key exists
4. Remove the respective key from the JSON file in the translations directory

## Updating Translations

1. Provide the key path (i.e. "edit.title")
2. Provide the updated translation for the target language (pl) with application name (default is "common")
3. Pull matching translation file and verify if the key exists
4. Update the respective key in the JSON file in the translations directory