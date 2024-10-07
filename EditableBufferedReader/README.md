## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

  @Override
    public String readLine() throws IOException {
        setRaw();
        System.out.print("\033[4L");
        Line buffer = new Line();
        char newChar;
        int cursor = 1;
        do {
            newChar = (char) read();
            // System.out.println((int) newChar);
            switch (newChar) {
                case (char) 13:
                    unsetRaw();
                    return buffer.toString();
                case (char) 4: // Control+D -> EOT (End Of Transmission)
                    unsetRaw();
                    out.updateView(buffer, cursor);
                    out.abort();
                    System.exit(1);
                case (char) 27: // ^
                    controlSequence(buffer);
                    break;
                default:
                    buffer.addChar(newChar, cursor);
                    cursor++;
                    out.updateView(buffer, cursor);
            }
        } while (true);
    }

    public void controlSequence(Line buffer) throws IOException {
        char newchar = (char) read();
        switch (newchar) {
            case (char) 91: // [
                escapeSequence(buffer);
                break;

            default:
                break;
        }
    }