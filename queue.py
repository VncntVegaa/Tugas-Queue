import tkinter as tk
from tkinter import messagebox
import os

# struktur data queue
queue = []
nomor = 1

# update tampilan
def tampilkan_antrian():
    listbox.delete(0, tk.END)
    for data in queue:
        listbox.insert(tk.END, f"{data[0]} - {data[1]}")

# ambil antrian
def ambil_antrian():
    global nomor
    nama = entry.get()

    if nama == "":
        return

    queue.append((nomor, nama))
    nomor += 1

    entry.delete(0, tk.END)
    tampilkan_antrian()

# panggil antrian
def panggil_antrian():
    if len(queue) == 0:
        messagebox.showinfo("Info", "Queue is empty")
        return

    data = queue.pop(0)
    teks = f"Queue number {data[0]}, {data[1]}, please proceed to the counter"

    messagebox.showinfo("Now Serving", teks)

    # 🔊 suara (Windows)
    speak(teks)

    tampilkan_antrian()

# fungsi suara
def speak(text):
    try:
        safe_text = text.replace("'", "")
        command = f'powershell -Command "Add-Type -AssemblyName System.Speech; (New-Object System.Speech.Synthesis.SpeechSynthesizer).Speak(\'{safe_text}\')"'
        os.system(command)
    except:
        pass

# GUI
root = tk.Tk()
root.title("Queue Simulation")

# input
tk.Label(root, text="Name").pack()
entry = tk.Entry(root)
entry.pack()

# tombol
tk.Button(root, text="Get Queue Number", command=ambil_antrian).pack(pady=5)
tk.Button(root, text="Call Next", command=panggil_antrian).pack(pady=5)

# list
listbox = tk.Listbox(root, width=30)
listbox.pack(pady=10)

root.mainloop()